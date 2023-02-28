import { MedicationService } from './../services/medication.service';
import { Component, OnInit } from '@angular/core';
import { ConsultationsService } from '../services/consultations.service';
import { LocalStorageService } from '../services/local-storage.service';

@Component({
  selector: 'app-consultations-table',
  templateUrl: './consultations-table.component.html',
  styleUrls: ['./consultations-table.component.scss']
})

export class ConsultationsTableComponent implements OnInit{

  userId: number;

  originalResponse: any;
  response: any;
  activeId: any;
  expDate: any;
  medications: Array<any> = [];
  medicationsIds: Array<any> = [];

  currentPage: any;
  pageStep: any;
  toShow: any;
  section: number = 0;
  dataResponse: any;
  pagesVisible: any;
  retrievedPages = false;

  constructor(private consultationService: ConsultationsService, private medicationService: MedicationService, private localStoreService: LocalStorageService) {
    this.currentPage = 0;
    this.pageStep = 10;
    this.userId = Number(localStoreService.getData("user"));
  }

  ngOnInit(): void {

    this.consultationService.getConsultations(this.userId).subscribe({
      next: consultations => {
        this.originalResponse = consultations;
        this.originalResponse = this.originalResponse.reverse();

        this.expDate = this.originalResponse[0].date_created;
        this.expDate = new Date(this.expDate);
        this.expDate = new Date(this.expDate.setMonth(this.expDate.getMonth()+1));
        this.expDate = new Date(this.expDate.setDate(this.expDate.getDate()-1));

        let start = Date.parse(this.originalResponse[0].date_created);
        let end = Date.parse(this.expDate);
        let curDate = Date.now();

        let bool = curDate.valueOf() >= start.valueOf() && curDate.valueOf() <= end.valueOf();

        if(bool){
          this.activeId = this.originalResponse[0].id;
        }
        else{
          this.activeId = -1;
        }

        this.readPaginatingData();

        if (this.pageStep < this.originalResponse.length)
          this.pagesVisible = Math.ceil(this.originalResponse.length / this.pageStep);
        else
          this.pagesVisible = 1;
                
        this.retrievedPages = true;

        for (let i = 0; i <  this.originalResponse.length; i++){
          let id = this.originalResponse[i].id;
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

  readPaginatingData(){
    this.response = this.originalResponse.slice(this.currentPage*this.pageStep,(this.currentPage+1)*this.pageStep);
  }

  setPageValue(value:any): void {
    this.currentPage = value - 1;
    this.readPaginatingData();
  }

  nextPage(){
    this.currentPage += 1;
    this.readPaginatingData();
  }  
  
  previousPage(){
    this.currentPage -= 1;
    this.readPaginatingData();
  }

}
