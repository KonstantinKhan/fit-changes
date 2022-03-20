import {Component, OnInit} from '@angular/core';
import {Chart, ChartData, ChartOptions, LegendItem, Plugin} from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";
import {Product} from "../../interfaces/product";

@Component({
  selector: 'app-energy-statistic',
  templateUrl: './energy-statistic.component.html',
  styleUrls: ['./energy-statistic.component.scss'],
})
export class EnergyStatisticComponent implements OnInit {

  sumEnergy = []

  product: Product = {
    productName: "Филе куриное",
    caloriesPerHundredGrams: 110.0,
    proteinsPerHundredGrams: 21,
    fatsPerHundredGrams: 0,
    carbohydratesPerHundredGrams: 0
  }
  labels: string[] = [];
  data: any;
  options: any;
  plugins: Plugin[] = []
  value: number = 0

  constructor() {
  }

  ngOnInit(): void {

    this.labels.push('Белки')
    this.labels.push('Жиры')
    this.labels.push('Углеводы')

    this.data = {
      labels: this.labels,
      datasets: [
        {
          backgroundColor: ['#EF9A9A', '#FFE082', '#C5E1A5'],
          data: [
            this.product.proteinsPerHundredGrams,
            this.product.fatsPerHundredGrams,
            this.product.carbohydratesPerHundredGrams
          ],
        }
      ]
    }
    this.setChartOptions()
    this.setPlugins()

  }

  setChartOptions() {
    this.options = {
      plugins: {
        datalabels: {
          anchor: "end",
          backgroundColor: 'grey',
          borderColor: 'white',
          borderRadius: 25,
          borderWidth: 2,
          color: 'white',
          padding: 4,
          display: function (context) {
            return context.dataset.data[context.dataIndex] != 0
          }
        },
        legend: {
          labels: {
            generateLabels: this.generateLabels,
            filter(item: LegendItem, data: ChartData): boolean {
              if (data.datasets[0].data[item.datasetIndex] == 0) {
                item.hidden = true
              }
              return true
            },
            boxWidth: 15,
          },
          display: true,
        },
      },
      layout: {
        padding: 8
      },
      radius: '90%',
    } as ChartOptions<'doughnut'>
  }

  setPlugins() {
    this.plugins.push(ChartDataLabels)
  }

  generateLabels(chart: Chart): LegendItem[] {
    return [
      {
        datasetIndex: 0,
        text: (chart.data.labels as string[])[0],
        fillStyle: (chart.data.datasets[0].backgroundColor as string[])[0]
      },
      {
        datasetIndex: 1,
        text: (chart.data.labels as string[])[1],
        fillStyle: (chart.data.datasets[0].backgroundColor as string[])[1]
      },
      {
        datasetIndex: 2,
        text: (chart.data.labels as string[])[2],
        fillStyle: (chart.data.datasets[0].backgroundColor as string[])[2]
      }
    ]
  }
}
