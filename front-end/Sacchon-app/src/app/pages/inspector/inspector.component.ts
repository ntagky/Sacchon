import { LocalStorageService } from './../../services/local-storage.service';
import { InspectorService } from './../../services/inspector.service';
import { Component, OnInit } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-inspector',
  templateUrl: './inspector.component.html',
  styleUrls: ['./inspector.component.scss']
})
export class InspectorComponent {

  userId: number;

  data: any = [];
  public view: any = [700, 400];
  public showXAxis = true;
  public showYAxis = true;
  public gradient = false;
  public showLegend = true;
  public showXAxisLabel = true;
  public xAxisLabel: string =  "Date";
  public showYAxisLabel = true;
  public yAxisCarbsLabel: string = "g";
  public yAxisGlucoseLabel: string = "mg/dL";
  public graphDataChart: any = [];

  colorScheme: Color = {
    domain: ['#99CCE5', '#FF7F7F'],
    group: ScaleType.Ordinal,
    selectable: true,
    name: 'Customer Usage',
  };

  colorSchemeGlucose: Color = {
    domain: ['#CC8899', '#FF7F7F'],
    group: ScaleType.Ordinal,
    selectable: true,
    name: 'Customer Usage',
  };

  isRetrieving: boolean = true;
  responseMeasurements: any;
  dateSigned: any;
  measurements: any;
  consultations: any;
  averageCarbs: any;
  averageGlucose: any;
  carbsChart: any = [];
  glucoseChart: any = [];

  constructor(
    private service: InspectorService,
    private localStoreService: LocalStorageService
  ) {
    this.userId = Number(localStoreService.getData("user"));
    this.readPagesCount();
  }

  readPagesCount() {
    this.service.get("http://localhost:9000/patient/" + this.userId + "/insights/query?start=2023-01-01&end=2023-02-18").subscribe({
      next: res => {
        this.responseMeasurements = res;
        this.dateSigned = this.responseMeasurements.dateSigned;
        this.measurements = this.responseMeasurements.measurements;
        this.consultations = this.responseMeasurements.consultations;
        this.averageCarbs = this.responseMeasurements.averageCarbs;
        this.averageGlucose = this.responseMeasurements.averageGlucose;
        this.isRetrieving = false;

        this.carbsChart = [
          {
            "name": "Carbs",
            "series" : this.responseMeasurements.carbsList
          }
        ];

        this.glucoseChart = [
          {
            "name": "Glucose",
            "series" : this.responseMeasurements.glucoseList
          }
        ]

        Object.assign(this, this.carbsChart);
        Object.assign(this, this.glucoseChart);

      }
    })
  }

}
