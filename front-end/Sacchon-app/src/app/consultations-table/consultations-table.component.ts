import { MedicationService } from './../services/medication.service';
import { Component, OnInit } from '@angular/core';
import { ConsultationsService } from '../services/consultations.service';

@Component({
  selector: 'app-consultations-table',
  templateUrl: './consultations-table.component.html',
  styleUrls: ['./consultations-table.component.scss']
})

export class ConsultationsTableComponent implements OnInit{

  response: any;
  activeId: any;
  expDate: any;
  medications: Array<any> = [];
  medicationsIds: Array<any> = [];
  patientId = 3;

  constructor(private consultationService: ConsultationsService, private medicationService: MedicationService) {}

  ngOnInit(): void {

    this.consultationService.getConsultations(this.patientId).subscribe({
      next: consultations => {
        this.response = consultations;
        this.response = this.response.reverse();

        this.expDate = this.response[0].date_created;
        this.expDate = new Date(this.expDate);
        this.expDate = new Date(this.expDate.setMonth(this.expDate.getMonth()+1));
        this.expDate = new Date(this.expDate.setDate(this.expDate.getDate()-1));

        let start = Date.parse(this.response[0].date_created);
        let end = Date.parse(this.expDate);
        let curDate = Date.now();

        let bool = curDate.valueOf() >= start.valueOf() && curDate.valueOf() <= end.valueOf();

        if(bool){
          this.activeId = this.response[0].id;
        }
        else{
          this.activeId = -1;
        }

        for (let i = 0; i <  this.response.length; i++){
          let id = this.response[i].id;
          this.medicationService.getMedications(id).subscribe({
            next: medications => {
              this.medications.push(medications);
              this.medicationsIds.push(id);
            }
          });
        }
      }
    });
  }

  getIndexFromId(i: any){
    return this.medicationsIds.indexOf(i);
  }
}
