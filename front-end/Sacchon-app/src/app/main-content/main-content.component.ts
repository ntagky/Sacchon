import { LocalStorageService } from './../services/local-storage.service';
import { DoctorService } from './../services/doctor.service';
import { MedicationService } from './../services/medication.service';
import { Component, OnInit, Input } from '@angular/core';
import { ConsultationsService } from '../services/consultations.service';

@Component({
  selector: 'app-main-content',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})

export class MainContentComponent implements OnInit{

  @Input() patientId: any;

  response: any;
  details: any;
  consDoctorFirstName: any;
  consDoctorLastName: any;
  doctorFirstName: any;
  doctorLastName: any;
  doctorEmail: any;
  date: any;
  consultationId: any;
  data: any;
  showAlert = true;
  seenResponse:any = null;

  welcomeMessage = "Sacchon";
  mottoMessage = "Control diabetes, live without limits";

  constructor(
    private consultationService: ConsultationsService,
    private medicationService: MedicationService,
    private doctorService: DoctorService
  ) { }

  ngOnInit(): void {


    // const hasShownAlert = localStorage.getItem('hasShownAlert');
    // if (hasShownAlert) {
    //   this.showAlert = false;
    // } else {

      // localStorage.setItem('hasShownAlert', 'true');
      setTimeout(() => {
        this.showAlert = false;
      }, 5000);
    // }



    this.consultationService.getConsultations(this.patientId).subscribe({
      next: consultations => {
        this.response = consultations;

        let latest = this.response.length - 1;

        this.consultationId = this.response[latest]["id"];
        this.details = this.response[latest]["details"];

        this.date = new Date(this.response[latest]["date_created"]);
        this.date = new Date(this.date.setMonth(this.date.getMonth()+1));
        this.date = new Date(this.date.setDate(this.date.getDate()-1));
        this.date = this.date.toLocaleDateString();

        this.consDoctorFirstName = 'Dr. ' + this.response[latest]["doctor_first_name"];
        this.consDoctorLastName = this.response[latest]["doctor_last_name"];

        this.medicationService.getMedications(this.consultationId).subscribe({
          next: medications => {
            this.data = medications;
          }
        });
      }
    });

    this.consultationService.getLastConsultationSeenStatus(this.patientId).subscribe({
      next: seenStatus => {this.seenResponse = seenStatus}
    });



    this.doctorService.getDoctor(this.patientId).subscribe({
      next: doctor => {
        this.response = doctor;

        this.doctorFirstName = this.response["firstName"];
        this.doctorLastName = this.response["lastName"];
        this.doctorEmail = this.response["email"];
      }
    })
  }



  printThisPage() {
    window.print();
  }








}
