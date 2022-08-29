/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

Chart.defaults.color = "#fff";
Chart.defaults.font.family = "sans-serif";
Chart.defaults.font.size = 14;

const printChartPolarMatters = () => {
  renderModelsChart();
};

const renderModelsChart = () => {
  const data = {
    labels: ["Lenguaje", "Ingles", "Matematicas", "Sociales", "Ciencias"],
    datasets: [
      {
        label: "My First Dataset",
        backgroundColor: [
          "rgb(255, 99, 132)",
          "rgb(75, 192, 192)",
          "rgb(255, 205, 86)",
          "rgb(201, 203, 207)",
          "rgb(54, 162, 235)",
        ],
   
        data: [10, 8, 9, 6, 10],
   

        radius: "90%",
        hoverOffset: 20,
        responsive: true,
        circular:true
      }
    ]
  };
  const options = {
    plugins: {
      legend: {
        position: "bottom",
        labels: {
          
          padding: 15,
          aling: "start",
          font: {
            size: 15
          }
        }
      }
    }
  };
  new Chart("IdpolarChartMatters", { type: "polarArea", data, options });
};
printChartPolarMatters();

