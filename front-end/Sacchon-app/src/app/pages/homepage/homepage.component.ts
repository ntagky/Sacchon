import { LocalStorageService } from './../../services/local-storage.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {

  patientId: Number = 0;

  constructor(private localStoreService: LocalStorageService) {
    this.patientId = Number(localStoreService.getData("user"));
  }

}
