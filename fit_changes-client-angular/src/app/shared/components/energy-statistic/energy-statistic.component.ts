import {Component, OnInit} from '@angular/core';
import {
  Chart,
  ChartData,
  ChartOptions,
  DoughnutControllerChartOptions,
  DoughnutControllerDatasetOptions,
  LegendItem
} from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";
import {Product} from "../../interfaces/product";

@Component({
  selector: 'app-ration-statistic',
  templateUrl: './energy-statistic.component.html',
  styleUrls: ['./energy-statistic.component.scss']
})
export class EnergyStatisticComponent implements OnInit {

  product: Product = {
    productName: "Филе куриное",
    caloriesPerHundredGrams: 110.0,
    proteinsPerHundredGrams: 23.1,
    fatsPerHundredGrams: 1.2,
    carbohydratesPerHundredGrams: 0
  }
  labels: string[] = [];
  chart: any;
  data: any;
  options: any;

  constructor() {
  }

  ngOnInit(): void {

    this.labels.push('Белки')
    this.labels.push('Жиры')
    this.labels.push('Углеводы')

    let ctx = document.getElementById('customDoughnut') as HTMLCanvasElement
    this.chart = new Chart(ctx, {
      data: {
        labels: ['Белки', 'Жиры', 'Углеводы'],
        datasets: [
          {
            backgroundColor: ['#CB9359', '#8B2B15', '#D6E1DD'],
            data: [
              this.product.proteinsPerHundredGrams,
              this.product.fatsPerHundredGrams,
              this.product.carbohydratesPerHundredGrams
            ],
          }
        ]
      },
      type: "doughnut",
      options: {
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
              boxWidth: 10,
            },
            display: true,
            onClick: this.onClick
          },
        },
        layout: {
          padding: 12
        },
        radius: 85,
        cutout: 65
      } as ChartOptions<'doughnut'>,
      plugins: [ChartDataLabels],
    })
  }

  onClick() {
    this.chart.hide(0, 0)
  }

  generateLabels(): LegendItem[] {
    return [
      {
        datasetIndex: 0,
        text: "Белки",
        fillStyle: '#CB9359'
      },
      {
        datasetIndex: 1,
        text: "Жиры",
        fillStyle: '#8B2B15'
      },
      {
        datasetIndex: 2,
        text: "Углеводы",
        fillStyle: '#D6E1DD',
      }
    ]
  }
}