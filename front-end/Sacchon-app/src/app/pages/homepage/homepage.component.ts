import { Router } from '@angular/router';
import { LocalStorageService } from './../../services/local-storage.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {

  patientId: Number = 0;

  constructor(private localStoreService: LocalStorageService, private router:Router) {
    this.patientId = Number(localStoreService.getData("user"));
    if (this.patientId <= 0) {
      this.localStoreService.clearData();
      this.router.navigateByUrl('/delete');
    }
  }
}
