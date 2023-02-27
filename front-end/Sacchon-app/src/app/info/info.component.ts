import { DeleteService } from './../services/delete.service';
import { InfoService } from './../services/info.service';
import { PatientService } from './../services/patient.service';
import { Component, OnInit } from '@angular/core'; import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-info', templateUrl: './info.component.html', styleUrls: ['./info.component.scss']
})

export class InfoComponent implements OnInit {
  data: any;
  patientId = 4;
  deleteRespone: any;
  constructor(private infoService: InfoService, private deleteService: DeleteService, private router: Router) { }
  ngOnInit(): void {
    this.getData();
  } getData() {
    this.infoService.get().subscribe({ next: response => this.data = response })
  }

  deleteData() {
    this.deleteService.delete().subscribe({
      next: data => {
        this.deleteRespone = data;
        this.router.navigateByUrl('/delete')
      }
    })
  }
}
