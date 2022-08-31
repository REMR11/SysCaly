# Copyright (c) 2007, 2019, Oracle and/or its affiliates. All rights reserved.
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License, version 2.0,
# as published by the Free Software Foundation.
#
# This program is also distributed with certain software (including
# but not limited to OpenSSL) that is licensed under separate terms, as
# designated in a particular file or component or in included license
# documentation.  The authors of MySQL hereby grant you an additional
# permission to link the program and your derivative works with the
# separately licensed software that they have included with MySQL.
# This program is distributed in the hope that it will be useful,  but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
# the GNU General Public License, version 2.0, for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software Foundation, Inc.,
# 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA

from mforms import App, Utilities, newBox, newPanel, newButton, newLabel, newTabView, newTabSwitcher, newTextEntry, newSelector, Form, newTreeView, OpenFile, SaveFile, OpenDirectory
import mforms
import errno
from wb_common import OperationCancelledError, InvalidPasswordError, dprint_ex, parentdir, joinpath
from workbench.log import log_debug

#===============================================================================
#
#================== =============================================================
class RemoteFileSelector(object):
    def __init__(self, ls, cwd, cd, title, dlg_type = OpenFile):
        self.ls = ls
        self.cwd = cwd
        self.cd = cd
        self.selection = ""
        self.title = title or "Select configuration file on remote server"
        self.dlg_type = dlg_type

    def get_filenames(self):
        return self.selection

    def on_change(self):
        if self.dlg_type == OpenDirectory:
            self.selection = self.curdir.get_string_value()
        else:
            selid = self.flist.get_selected_node()
            if selid:
                fname = selid.get_string(0)
                self.selection = joinpath(self.curdir.get_string_value(),fname)

    def on_cd(self, row, column):
        fname = None
        selid = self.flist.get_selected_node()
        if selid:
            fname = selid.get_string(0)
        self.chdir(fname, False)

    def try_cd(self, fname):
        success = False
        target_is_file = False

        try:
            success = self.cd(fname)
            if success == -1:
                success = False
                target_is_file = True
            
        except Exception as e:
            print(e)
            if len(e.args) > 0 and e[0] == errno.ENOTDIR:
                success = False
                target_is_file = True
            else:
                raise

        return (success, target_is_file)

    def chdir(self, fname, is_full_path):
#         import traceback
#         traceback.print_stack()
        
        success = False
        if fname is not None and fname != "":
            log_debug("Directory changed: "+ fname + " was full path: " + str(is_full_path) + "\n")
            path = fname if is_full_path else joinpath(self.curdir.get_string_value(), fname)

            (success, target_is_file) = self.try_cd(path)

            # The change failed... but still could be a valid
            if not success:
                done = target_is_file

                # If the failure is because an attempt to cd to a file was done
                if target_is_file:
                    # If saving a file, we need to confirm the user wants to override the existing file
                    if self.dlg_type == SaveFile:
                        if mforms.Utilities.show_warning('File Already Exist',
                                                         "The file %s already exists.\n Do you want to replace it?" % path,
                                                         "Yes", "No", "") != mforms.ResultOk:
                            done = False

                # When saving a file, an invalid path could mean a new file, we need to
                # check if the parent folder exists and if so... then we are done!
                elif self.dlg_type == SaveFile:
                    (done, target_is_file) = self.try_cd(parentdir(path))

                    if not done:
                        mforms.Utilities.show_warning('Invalid Path',
                                                         "The path to %s is invalid." % path,
                                                         "Ok", "", "")
                   
                # Done will be true when:
                # - OpenFile on a file path
                # - SaveFile on an existing path and user indicated to override
                # - SaveFile on an unexisting path with a valid directory
                if done:
                    self.selection = path
                    self.form.close()
                    return
        else:
            log_debug("Directory not changed\n")

        # Updates the file list only in case a cd has been done
        if success or fname is None:
            curdir = self.cwd()
            log_debug("chdir: Current dir is: " + curdir+"\n")
            #if curdir:
            #    if curdir[-1] != "/":
            #        curdir += "/"
            self.update_text(curdir)
            self._invalid_text = False
            
            self.flist.clear()
            
            (disr, files) = ((),())
            entries = self.ls(curdir)
            dirs = [d['name'] for d in entries if d['isDir'] == 1 and d['name'] != ".." and d['name'] != "."]
            files = [f['name'] for f in entries if f['isDir'] == 0 and f['name'] != ".." and f['name'] != "."]
            dirs.sort()
            files.sort()
            
            row_id = self.flist.add_node()
            row_id.set_icon_path(0, 'folder')
            row_id.set_string(0, '..')

            for d in dirs:
                row_id = self.flist.add_node()
                row_id.set_icon_path(0, 'folder')
                row_id.set_string(0, d)
     
            for f in files:
                row_id = self.flist.add_node()
                row_id.set_string(0, f)

    def cancel_action(self):
        self.selection = None

    def accept_action(self):
        # Invalid text indicates an entry was typed and has not been validated
        if self._invalid_text:
            dir = self.curdir.get_string_value().encode("utf8")
            self.chdir(dir, True)
        else:            
          self.form.close()

    def text_action(self, action):
        if action == mforms.EntryActivate:
            dir = self.curdir.get_string_value().encode("utf8")
            self.chdir(dir, True)

    def update_text(self, text):
        self._updating = True
        self.curdir.set_value(text)
        self._updating = False

    def text_changed(self):
        if not self._updating:
            self._invalid_text = True

    def run(self):
        self._updating = False
        self._invalid_text = False
        self.form  = Form(None, mforms.FormResizable)
        self.form.set_title(self.title)
        self.flist = newTreeView(mforms.TreeFlatList)
        self.curdir = newTextEntry()

        self.flist.add_column(mforms.IconStringColumnType, "File", 400, False)
        self.flist.end_columns()

        self.curdir.add_changed_callback(self.text_changed)
        self.curdir.add_action_callback(self.text_action)
        self.flist.add_activated_callback(self.on_cd)
        self.flist.add_changed_callback(self.on_change)

        accept = newButton()
        accept.set_text("OK")
        cancel = newButton()
        cancel.set_text("Cancel")
        button_box = newBox(True)
        button_box.set_padding(10)
        button_box.set_spacing(8)
        Utilities.add_end_ok_cancel_buttons(button_box, accept, cancel)

        box = newBox(False) # Hosts all entries on that dialog.
        box.set_padding(10)
        box.set_spacing(10)
        box.add(self.curdir, False, False)
        box.add(self.flist, True, True)
        box.add(button_box, False, False)

        self.form.set_content(box)
        self.form.set_size(500, 400)

        cancel.add_clicked_callback(self.cancel_action)
        accept.add_clicked_callback(self.accept_action)

        self.form.relayout()
        self.form.center()

        self.on_cd(0, 0)

        # Don't use the accept button in run_modal or you won't be able to press <enter>
        #  to change the path via the top edit control.
        self.form.run_modal(None, cancel)

#-------------------------------------------------------------------------------
def remote_file_selector(profile, password_delegate, ssh=None, title=None, dlg_type = OpenFile):
    close_ssh = False
    if not ssh:
        #TODO this needs to be rewritten
        close_ssh = True
        try:
            ssh = wb_admin_ssh.WbAdminSSH()
            ssh.wrapped_connect(profile, password_delegate)
        except OperationCancelledError:
            ssh = None
        except wb_admin_ssh.SSHDownException:
            ssh = None

    file_names = []

    if ssh is not None and ssh.isConnected() == 1:
        rfs = RemoteFileSelector(ls = ssh.ls, cwd = ssh.pwd, cd = ssh.cd, title = title, dlg_type = dlg_type)
        rfs.run()
        result = rfs.get_filenames()
        if result is not None:
            file_names = result

        if close_ssh:
            ssh.close()

    ret = ""
    if type(file_names) is list:
        if len(file_names) > 0:
            ret = file_names[0]
    else:
        ret = file_names

    return ret
