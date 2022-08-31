# Copyright (c) 2012, 2019, Oracle and/or its affiliates. All rights reserved.
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

import queue

from wb import DefineModule
import grt
import mforms
import migration_main

ModuleInfo = DefineModule(name= "Migration", author= "Oracle Corp.", version="1.0")

tab_reference = None

#-------------------------------------------------------------------------------
def handle_view_close(view):
    global tab_reference
    tab_reference = None
    view.cleanup()
    return True

#-------------------------------------------------------------------------------
@ModuleInfo.plugin("wb.migration.open", type="standalone", caption= "Migration Plugin",  pluginMenu= "Migration", accessibilityName="Migration")
@ModuleInfo.export(grt.INT)
def openMigration():
    global tab_reference
    app = mforms.App.get()
    if tab_reference:
        app.select_view(tab_reference)
        return

    mgview = migration_main.Migration()

    app.dock_view(mgview, "maintab")
    mgview.set_title("Migration")

    tab_reference = mgview
    mgview.on_close(lambda: handle_view_close(mgview)) # TODO: create weak ref

    app.set_status_text("Migration Wizard was started")

    return 1


@ModuleInfo.plugin("wb.migration.showTypeMapEditor", type="standalone", caption= "Type Mapping Editor",  pluginMenu= "Migration", accessibilityName="Type Mapping Editor")
@ModuleInfo.export(grt.INT)
def showTypeMapEditor():
    import datatype_mapping_editor
    return datatype_mapping_editor.run()
    
    
