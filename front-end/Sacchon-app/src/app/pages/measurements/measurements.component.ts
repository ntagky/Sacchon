import { LocalStorageService } from './../../services/local-storage.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-measurements',
  templateUrl: './pages/measurements.component.html',
  styleUrls: ['./pages/measurements.component.scss']
})
export class MeasurementsComponent {

  userId: Number = 0;

  constructor(private localStoreService: LocalStorageService){
    this.userId = Number(localStoreService.getData("user"));
  }
}
