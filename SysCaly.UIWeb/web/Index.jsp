<%-- 
    Document   : Index
    Created on : 19 ago 2022, 16:11:06
    Author     : Fsociety
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css"
              rel="stylesheet"
              />
    </head>
   <body>
    <div class="xlcontainer mx-auto bg-gray-900">
      <!--CARDS Line 1-->
      <div class="grid sm:grid-row-2 lg:grid-row-4 grind-1 pl-8 m-2">
        <div class="flex justify-between">
          <div><h1 class="text-9xl text-gray-100  p-4">Inicio</h1></div>
          <div>
            <button class="rounded-md transition duration-200 bg-gray-500  hover:bg-gray-200 m-4 px-6 shadow-cyan-500 py-1">Reporte general</button>
          </div>
        </div>

        <div
          class="grid sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-4 text-white"
        >
          <button
            class="bg-gray-800 hover:bg-gray-700 border-l-4 border-green-500 rounded-r-lg m-2 duration-1000"
          >
            <div class="p-2">
              <div class="grid xl:grid-cols-2 sm:grid-cols-1">
                <p class="font-sans font-bold text-sm text-green-400 pl-5">
                  LISTA DE ESTUDIANTES <br /><span class="text-green-200"
                    >Activos</span
                  >
                </p>
                <div class="font-sans font-bold text-2xl text-neutral-500 pl-5">
                  2,000
                </div>
              </div>
              <div class="pl-5">
                <div class="grid grid-cols-3 gap-4">
                  <div class="...">
                    <svg
                      class="w-5 h-5"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                      ></path>
                    </svg>
                  </div>
                  <div class="col-span-2">
                    <div
                      id="Chat-Number-Teachers"
                      class="bg-green-300 rounded-xl"
                    >
                      -
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </button>

          <button
            class="bg-gray-800 hover:bg-gray-700 border-l-4 border-yellow-500 rounded-r-lg m-2 duration-1000"
          >
            <div class="p-2">
              <div class="grid xl:grid-cols-2 sm:grid-cols-1">
                <p class="font-sans font-bold text-sm text-yellow-400 pl-5">
                  LISTA DE DOCENTES <br /><span class="text-yellow-200"
                    >Activos</span
                  >
                </p>
                <div class="font-sans font-bold text-2xl text-neutral-500 pl-5">
                  60
                </div>
              </div>
              <div class="pl-5">
                <div class="grid grid-cols-3 gap-4">
                  <div class="...">
                    <svg
                      class="w-6 h-6"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"
                      ></path>
                    </svg>
                  </div>
                  <div class="col-span-2 ...">
                    <div
                      id="Chat-Number-Teachers"
                      class="bg-yellow-300 rounded-xl"
                    >
                      -
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </button>

          <button
            class="bg-gray-800 hover:bg-gray-700 border-l-4 border-pink-500 rounded-r-lg m-2 duration-1000"
          >
            <div class="p-2">
              <div class="grid xl:grid-cols-2 sm:grid-cols-1">
                <p class="font-sans font-bold text-sm pl-5 text-pink-400">
                  GRADOS Y SECCIONES <br /><span class="text-pink-200"
                    >Activos</span
                  >
                </p>
                <div class="font-sans font-bold text-2xl text-neutral-500 pl-5">
                  30
                </div>
              </div>
              <div class="pl-5">
                <div class="grid grid-cols-3 gap-4">
                  <div class="...">
                    <svg
                      class="w-6 h-6"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M3 10h18M3 14h18m-9-4v8m-7 0h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z"
                      ></path>
                    </svg>
                  </div>
                  <div class="col-span-2">
                    <div
                      id="Chat-Number-Teachers"
                      class="bg-pink-300 rounded-xl"
                    >
                      -
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </button>

          <button
            class="bg-gray-800 hover:bg-gray-700 border-l-4 border-teal-300 rounded-r-lg m-2 duration-1000"
          >
            <div class="p-2">
              <div class="grid xl:grid-cols-2 sm:grid-cols-1">
                <p class="font-sans font-bold text-md pl-5 text-teal-300">
                  ¡ACTUALIZACION!... <br /><span class="text-teal-200"
                    >Proximamente</span
                  >
                </p>
                <div class="font-sans font-bold text-2xl text-neutral-500 pl-5">
                  q(≧▽≦q)
                </div>
              </div>
              <div class="pl-5">
                <div class="grid grid-cols-3 gap-4">
                  <div class="...">
                    <svg
                      class="w-6 h-6"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M13 5l7 7-7 7M5 5l7 7-7 7"
                      ></path>
                    </svg>
                  </div>
                  <div class="col-span-2 ...">
                    <div id="Chat-Number-Teachers" class="bg-red-500"></div>
                  </div>
                </div>
              </div>
            </div>
          </button>
        </div>
      </div>

      <!--CHARTS API-->
      <div class="grid md:grid-row-1 lg:grid-row-4 pl-8">
        <div
          class="grid md:grid-cols-1 lg:grid-cols-3 gap-4 border border-white"
        >
          <div class="md:col-span-2 m-1">
            <div class="m-2">
              <div
                class="flex justify-between transition delay-150 duration-300 bg-gray-800 hover:bg-teal-300 border-b-4 border-teal-300 rounded-t-xl p-3 m-2 text-white hover:text-gray-900 font-bold"
              >
                <div
                  class="inline-block align-middle font-sans font-bold text-md"
                >
                  <p>Materias</p>
                </div>
                <div>
                  <button
                    id="IdButtonAnimateBounce"
                    class="hover:text-teal-900 transition hover:scale-110 hover:-translate-y-1 animate-bounce"
                  >
                    <svg
                      class="w-8 h-8"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4"
                      ></path>
                    </svg>
                  </button>
                </div>
              </div>
              <div class="text-white rounded-b-xl bg-gray-800 m-2">
                <figure>
                  <canvas id="IdBarChartMatters" class="pb-6"></canvas>
                </figure>
              </div>
            </div>
          </div>
          <div class="m-1">
            <div
              class="flex justify-between transition delay-150 duration-300 bg-gray-800 hover:bg-pink-300 border-b-4 border-pink-300 rounded-t-xl p-3 m-2 text-white hover:text-gray-900 font-bold"
            >
              <div
                class="inline-block align-middle font-sans font-bold text-md"
              >
                <p>Materias</p>
              </div>
              <div>
                <button
                  id="IdButtonAnimateBounce"
                  class="hover:text-pink-900 animate-bounce"
                  onclick=""
                >
                  <svg
                    class="w-8 h-8"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4"
                    ></path>
                  </svg>
                </button>
              </div>
            </div>
            <div class="text-white rounded-b-xl bg-gray-800 m-2">
              <figure>
                <canvas id="IdpolarChartMatters" class="pb-6"></canvas>
              </figure>
            </div>
          </div>
        </div>
        <div
          class="grid sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-4 bg-indigo-800 text-whie border border-white"
        >
          <div class="m-2 bg-yellow-700">columnas</div>
          <div class="m-2 bg-yellow-700">columnas</div>
          <div class="m-2 bg-yellow-700">columnas</div>
          <div class="m-2 bg-yellow-700">columnas</div>
        </div>
      </div>

      <div class="bg-blue-600 grid ms:grid-row-1 pl-8">
        <div
          class="bg-blue-400 grid ms:grid-cols-1 md:grid-cols-4 gap-2 border border-white"
        >
          <div class="md:col-span-2 bg-red-300 m-1">NOTICIAS</div>
          <div class="md:col-span-2 bg-red-300 m-1">CLASES</div>
        </div>
      </div>
      <div class="bg-blue-600 grid ms:grid-row-1 pl-8">
        <div
          class="bg-blue-400 grid ms:grid-cols-1 md:grid-cols-4 gap-4 border border-white"
        >
          <div class="md:col-span-2 bg-red-300 m-2">GRAFICA</div>
          <div class="md:col-span-2 bg-red-300 m-2">GRAFICA</div>
        </div>
      </div>

      <div class="bg-blue-600 grid ms:grid-row-1 pl-8">
        <div
          class="bg-blue-400 grid ms:grid-cols-1 md:grid-cols-3 gap-4 border border-white"
        >
          <div class="md:col-span-2 bg-red-300 m-1">
            <div class="grid grid-cols-2">
              <div class="border m-2">BUENOS DIAS</div>
              <div class="border m-2">BUESNOS DIAS</div>
              <div class="border m-2">hola</div>
              <div class="border m-2">hola</div>
              <div class="border m-2">hola</div>
              <div class="border m-2">hola</div>
            </div>
          </div>

          <div class="bg-red-300 m-1">PROFESORES</div>
        </div>
      </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="js/ChartsJSAPI/chart-bar-Matters.js"></script>
    <script src="js/ChartsJSAPI/chat-polar-matters.js"></script>
  </body>
</html>
