import { LocalStorageService } from 'src/app/services/local-storage.service';
import { PatientService } from './../services/patient.service';
import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit{

  @Input() patientId: any;
  @Input() page: any;

  response: any;
  firstName: any;
  lastName: any;
  initials: any;
  toPage: string = '/login';

  constructor(private patientService: PatientService, private localStorageService: LocalStorageService, private router: Router){}

  ngOnInit(): void {
    if (this.patientId > 0) {
      this.toPage = '/home';

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

  deleteData(){
    this.localStorageService.clearData();
    this.router.navigateByUrl('/signout')
  }
}
