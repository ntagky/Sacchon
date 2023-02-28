import { PatientService } from './../services/patient.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit{

  response: any;
  firstName: any;
  lastName: any;
  initials: any;

  patientId = 3;

  constructor(private patientService: PatientService){}

  ngOnInit(): void {
    this.patientService.getPatient(this.patientId).subscribe({
      next: patient => {
        this.response = patient;

        this.firstName = this.response[0]["firstName"];
        this.lastName = this.response[0]["lastName"];

        this.initials = this.firstName[0] + this.lastName[0];

        
      }
    })
  }
}
