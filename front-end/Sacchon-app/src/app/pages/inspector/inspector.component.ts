import { LocalStorageService } from './../../services/local-storage.service';
import { InspectorService } from './../../services/inspector.service';
import { Component, OnInit } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-inspector',
  templateUrl: './inspector.component.html',
  styleUrls: ['./inspector.component.scss']
})
export class InspectorComponent {

  userId: number;

  expiration: any;
  dtm: any;

  data: any = [];
  public view: any = [700, 400];
  public showXAxis = true;
  public showYAxis = true;
  public gradient = false;
  public showLegend = true;
  public showXAxisLabel = true;
  public xAxisLabel: string =  "Date";
  public xAxisTimeLabel: string =  "Time";
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

  colorSchemeGlucose1: Color = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA'],
    group: ScaleType.Ordinal,
    selectable: true,
    name: 'Customer Usage',
  };

  isRetrieving: boolean = true;
  responseMeasurements: any;
  responseDaysRecords: any;
  startingDate: any;
  dateSigned: any;
  daysDescription: any;
  lastDate: Date = new Date();
  dateToday: Date = new Date();
  lastMonth: Date = new Date(new Date().setDate(new Date().getDate() - 30));
  toDate: any;
  measurements: any;
  consultations: any;
  averageCarbs: any;
  averageGlucose: any;
  carbsChart: any = [];
  glucoseChart: any = [];
  glucoseDayBar: any = [];
  barChartHidden: boolean = true;

  constructor(
    private service: InspectorService,
    private localStoreService: LocalStorageService
  ) {
    this.userId = Number(localStoreService.getData("user"));
    this.getDateAssigned();
    this.readDataInDates(this.lastMonth, this.dateToday);
  }

  getDateAssigned() {
    this.service.get("http://localhost:9000/patient/"+ this.userId + "/signed-date").subscribe({
      next: res => {
        this.dateSigned = res;
        this.startingDate = res;
        this.toDate = new Date().toISOString().substring(0,10);
      }
    });
  }

  convertVariableToDateFormat(dt: any) {
    try {
      return dt.toISOString().slice(0, 10);
    } catch {
      // Date already formatted
      return dt;
    }
  }

  readDataInDates(start: any, end: any) {
    start = this.convertVariableToDateFormat(start);
    end = this.convertVariableToDateFormat(end)

    this.service.get("http://localhost:9000/patient/" + this.userId + "/insights/query?start=" + start + "&end="+ end).subscribe({
      next: res => {
        this.responseMeasurements = res;
        console.log(this.responseMeasurements.daysDescription);
        this.daysDescription = this.responseMeasurements.daysDescription;
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
    });
  }

  addEvent(isStart: any, event: any) {
    if (isStart && event.target.value != null )
      this.startingDate = event.target.value.format('yyyy-MM-DD');
    else if (!isStart && event.target.value != null)
      this.toDate = event.target.value.format('yyyy-MM-DD');

    console.log(this.startingDate);
    if (this.startingDate < this.toDate && event.target.value != null) {
      this.readDataInDates(this.startingDate, this.toDate);
    }
  }

  onSelect(dateSelected: any) {
    this.barChartHidden = false;
    this.service.get("http://localhost:9000/patient/" + this.userId + "/glucose/" + dateSelected.name + "/insights").subscribe({
      next: res => {
        this.responseDaysRecords = res;
        console.log("Donw");
        console.log(this.responseDaysRecords.glucoseList);

        this.glucoseDayBar = this.responseDaysRecords.glucoseList;

        Object.assign(this, this.glucoseDayBar);
      }
    })
  }
}

