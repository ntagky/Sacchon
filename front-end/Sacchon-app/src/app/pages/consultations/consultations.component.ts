import { Component } from '@angular/core';
import { LocalStorageService } from 'src/app/services/local-storage.service';

@Component({
  selector: 'app-consultations',
  templateUrl: './consultations.component.html',
  styleUrls: ['./consultations.component.scss']
})
export class ConsultationsComponent {
  userId: number;

  constructor(
    private localStoreService: LocalStorageService
  ) {
    this.userId = Number(localStoreService.getData("user"));
  }
}
