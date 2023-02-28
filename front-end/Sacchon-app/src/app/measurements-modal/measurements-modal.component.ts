import { Component, OnInit, Input } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { FormBuilder, FormArray, Validators } from '@angular/forms';
import { MeasurementService } from '../services/measurements.service';

@Component({
  selector: 'app-measurements-modal',
  templateUrl: './measurements-modal.component.html',
  styleUrls: ['./measurements-modal.component.scss']
})
export class ModalMeasurementsComponent implements OnInit {

  @Input() patientId: any;

  id: number | null = null;
  date: string | null = null;
  carbs: number | null = null;
  startingCarbs: any;
  carbExistance: any;
  glucoseRecords: any | null = null;
  glucoseMeasurementArray: any;

  createForm: any;

  response: any;
  glucoseRecordsResponse: any;

  time1: Date = new Date();
  ismeridian = false;

  constructor(
    public modalRef: MdbModalRef<ModalMeasurementsComponent>,
    private service: MeasurementService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.createForm = this.fb.group({
      date: this.date,
      carbsMeasurement: [this.carbs, [Validators.required, Validators.min(1)]],
      glucoseMeasurement: this.fb.array([])
    });

    this.startingCarbs = this.carbs;
    if (this.carbs == null)
      this.carbExistance = false;
    else
      this.carbExistance = this.carbs > 0;

    this.readGlucoseRecords();
  }

  get glucoseMeasurement() {
    return this.createForm.controls["glucoseMeasurement"] as FormArray;
  }

  addGlucoseRecord(id: any, grams:any, hh:any, mm:any) {
    const glucoseForm = this.fb.group({
      id: id,
      measurement: [grams,[Validators.required, Validators.min(1)]],
      startingMeasurement: [grams],
      hour: [hh, Validators.required],
      startingHour: [hh],
      minute: [mm, Validators.required],
      startingMinute: [mm],
    });
    this.glucoseMeasurement.push(glucoseForm);
  }

  deleteGlucoseMeasurement(glucoseIndex: number) {
    this.glucoseMeasurement.removeAt(glucoseIndex);
  }

  close(): void {
    this.modalRef.close({
      id: this.id,
      carbs: this.carbs,
      avgGlucose: this.getAverageGlucose(),
      glucoseRecords: this.getSumbittedGlucoseRecordCount()
    })
  }

  createCabrs() {
    const id = 3;

    const data = {
      id: 0,
      date: this.createForm.get('date').value,
      measurement: this.createForm.get('carbsMeasurement').value,
      units: 'g'
    };

    this.service.post(data, "http://localhost:9000/patient/" + this.patientId + "/carbs").subscribe({
      next: res => {
        this.response = res;
        this.createForm.patchValue({'carbsMeasurement': data.measurement});
        this.carbs = data.measurement;
        this.startingCarbs = this.carbs;
      }
    })
  }

  updateCabrs() {
    const id = 3;
    this.service.put("http://localhost:9000/patient/" + this.patientId + "/carbs/" + this.createForm.get('date').value + "/update/" + this.createForm.get('carbsMeasurement').value).subscribe({
      next: res => {
        this.response = res;
        this.createForm.patchValue({'carbsMeasurement': this.createForm.get('carbsMeasurement').value});
        this.carbs = this.createForm.get('carbsMeasurement').value
        this.startingCarbs = this.carbs;
      }
    })
  }

  readGlucoseRecords() {
    this.service.get("http://localhost:9000/patient/" + this.patientId + "/glucose/" + this.date).subscribe({
      next: glucoseRecordsResponse => {
        this.glucoseRecordsResponse = glucoseRecordsResponse;

        const jsonResponse = JSON.parse(JSON.stringify(glucoseRecordsResponse));

        var i = 0;
        if (jsonResponse != null)
          for (i = 0; i < jsonResponse.length; i++) {
            console.log(jsonResponse[i]);
            this.addGlucoseRecord(
              jsonResponse[i].id,
              jsonResponse[i].measurement,
              jsonResponse[i].time.split(':')[0],
              jsonResponse[i].time.split(':')[1]
            )
          }

        this.addGlucoseRecord(
          0, "", "", ""
        )

       }
    });
  }

  toggleMode(): void {
    this.ismeridian = !this.ismeridian;
  }

  deleteCarbs():void {
    this.service.delete("http://localhost:9000/patient/" + this.patientId + "/carbs/" + this.date).subscribe({
      next: res => {
        this.createForm.patchValue({'carbsMeasurement': 0});
        this.carbs = 0
        this.startingCarbs = 0;
        this.carbExistance = false;
      }
    });
  }

  saveCarbs(): void {
    if (this.carbExistance) {
      this.updateCabrs();
    } else {
      this.createCabrs();
      this.carbExistance = true;
    }
  }

  get isCarbsSaveValid(): boolean {
    return this.createForm.get('carbsMeasurement')?.invalid || this.createForm.get('carbsMeasurement').value == this.startingCarbs;
  }

  get isCarbsDeleteValid(): boolean {
    return this.createForm.get('carbsMeasurement').value != this.startingCarbs;
  }

  // Glucose Records stuff

  getGlucoseValue(idx: Number): number {
    return Number(this.createForm.controls["glucoseMeasurement"].at(idx).value['startingMeasurement']);
  }

  getGlucoseHourValue(idx: Number): number {
    return Number(this.createForm.controls["glucoseMeasurement"].at(idx).value['startingHour']);
  }

  getGlucoseMinuteValue(idx: Number): number {
    return Number(this.createForm.controls["glucoseMeasurement"].at(idx).value['startingMinute']);
  }

  isGlucoseSaveValid(idx: Number): boolean {
    return this.createForm.controls["glucoseMeasurement"].at(idx)?.invalid ||
      this.createForm.controls["glucoseMeasurement"].at(idx).value['measurement'] == this.createForm.controls["glucoseMeasurement"].at(idx).value['startingMeasurement'];
  }

  isGlucoseDeleteValid(idx: Number): boolean {
    return this.createForm.controls["glucoseMeasurement"].at(idx).value['startingMeasurement'] == 0 ;
  }

  saveGlucoseRecord(idx: Number): void {
    let currentForm = this.createForm.controls["glucoseMeasurement"].at(idx);
    if (currentForm.value['startingMeasurement'] == '') {
      console.log("Creating..");
      this.createGlucoseRecord(idx);
    } else {
      console.log("Updating..");
      this.updateGlucoseRecord(idx);
    }
  }

  createGlucoseRecord(idx: any) {
    const currentForm = this.createForm.controls["glucoseMeasurement"].at(idx);
    const data = {
      hour: currentForm.value['hour'],
      minute: currentForm.value['minute'],
      second: 0,
      measurement: currentForm.value['measurement'],
    };

    this.service.postData(data, "http://localhost:9000/patient/" + this.patientId + "/glucose/" + this.date).subscribe({
      next: res => {
        this.response = res;

        currentForm.patchValue({
          'id': res,
          'measurement': data.measurement,
          'startingMeasurement': data.measurement,
          'hour': data.hour,
          'startingHour': data.hour,
          'minute': data.minute,
          'startingMinute': data.minute,
        });

        this.getActiveGlucoseRecords();
      }
    })
  }

  updateGlucoseRecord(idx: Number) {
    const currentForm = this.createForm.controls["glucoseMeasurement"].at(idx);
    const data = {
      hour: currentForm.value['hour'],
      minute: currentForm.value['minute'],
      second: 0,
      measurement: currentForm.value['measurement'],
    };

    this.service.putData(data, "http://localhost:9000/patient/glucose/record/" + currentForm.value['id'] + "/update").subscribe({
      next: res => {
        this.response = res;
        currentForm.patchValue({
          'measurement': data.measurement,
          'startingMeasurement': data.measurement,
          'hour': data.hour,
          'startingHour': data.hour,
          'minute': data.minute,
          'startingMinute': data.minute,
        });

        this.getActiveGlucoseRecords();
      }
    })
  }

  deleteGlucoseRecord(idx: number):void {
    const currentForm = this.createForm.controls["glucoseMeasurement"].at(idx);

    let activeRecords = 0;
    for (var i=0; i<this.createForm.controls["glucoseMeasurement"].length; i++) {
      console.log(this.createForm.controls["glucoseMeasurement"].at(i).value['id']);
      if (this.createForm.controls["glucoseMeasurement"].at(i).value['id'] != 0)
      activeRecords++;
    }

    if (activeRecords > 1) {  // Delete Glucose Record
      this.service.delete("http://localhost:9000/patient/glucose/record/" + currentForm.value['id'] + "/delete").subscribe({
        next: res => {
          this.response = res;
        }
      });
    } else {  // Delete Glucose table
      console.log("Deleting whole table..");
      this.service.delete("http://localhost:9000/patient/" + this.patientId + "/glucose/" + this.date + "/delete").subscribe({
        next: res => {
          this.response = res;
        }
      });
    }

    if (this.createForm.controls["glucoseMeasurement"].length > 1) {
      this.deleteGlucoseMeasurement(idx);
    } else {
      currentForm.patchValue({
        'measurement': 0,
        'startingMeasurement': 0,
        'hour': 0,
        'startingHour': 0,
        'minute': 0,
        'startingMinute': 0,
      });
    }
  }

  getActiveGlucoseRecords(): void {
    let activeRecords = 0;
    for (var i=0; i<this.createForm.controls["glucoseMeasurement"].length; i++) {
      if (this.createForm.controls["glucoseMeasurement"].at(i).value['startingMeasurement'] != 0)
        activeRecords++;
      else
        break;
    }

    if (activeRecords == this.createForm.controls["glucoseMeasurement"].length)
      this.addGlucoseRecord(0, 0, "", "");
  }

  getAverageGlucose(): number {
    let activeRecords = [];
    for (var i=0; i<this.createForm.controls["glucoseMeasurement"].length; i++) {
      if (this.createForm.controls["glucoseMeasurement"].at(i).value['startingMeasurement'] != 0)
        activeRecords.push(this.createForm.controls["glucoseMeasurement"].at(i).value['startingMeasurement']);
    }

    if (activeRecords.length == 0)
      return 0;

    return activeRecords.reduce((partialSum, a) => partialSum + a, 0) / activeRecords.length;
  }

  getSumbittedGlucoseRecordCount(): number {
    let activeCounter = 0;
    for (var i=0; i<this.createForm.controls["glucoseMeasurement"].length; i++) {
      if (this.createForm.controls["glucoseMeasurement"].at(i).value['startingMeasurement'] > 0)
        activeCounter++;
    };
    return activeCounter;
  }
}
