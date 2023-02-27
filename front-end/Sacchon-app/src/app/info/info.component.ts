import { InfoService } from './../services/info.service';
import { PatientService } from './../services/patient.service';
import { Component, OnInit } from '@angular/core';
import {RouterModule} from  '@angular/router';


@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.scss']
})
export class InfoComponent implements OnInit {

  data:any;
  patientId = 4;



  constructor(private infoService:InfoService){


  }



  ngOnInit():void{

    this.getData();
  }

  getData(){

     this.infoService.get().subscribe(
    {
       next: response =>this.data = response,

    }


  )}



  }
