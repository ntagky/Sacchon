import { LocalStorageService } from './../../services/local-storage.service';
import { Component } from '@angular/core';
import { ModalMeasurementsComponent } from '../../measurements-modal/measurements-modal.component';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { MeasurementService } from '../../services/measurements.service';

@Component({
  selector: 'app-measurements',
  templateUrl: './pages/measurements.component.html',
  styleUrls: ['./pages/measurements.component.scss']
})
export class MeasurementsComponent {

  modalRef: MdbModalRef<ModalMeasurementsComponent> | null = null;
  dataResponse: any;
  pagesRespone: any;
  patientId: any;
  currentPage: any;
  realPage: any;
  pageStep: any;
  toShow: any;
  pagesVisible: any;
  lastPageIndex: any;
  retrievedPages = false;
  retrievedTable = false;

  constructor(private modalService: MdbModalService, private service: MeasurementService, private localStoreService: LocalStorageService) {
    // this.patientId = 2;
    this.userId = Number(localStoreService.getData("user"));
    this.currentPage = 0;
    this.pageStep = 10;

    this.readPagesCount();
    this.readPaginatingData();
  }

  ngOnInit(): void {

  }

  openModal(selectedId: number, dateSelected: string, carbsIntake: any, glucoseProvided:any) {
    this.modalRef = this.modalService.open(ModalMeasurementsComponent, {
      data: {
        id: selectedId,
        date: dateSelected,
        carbs: carbsIntake,
        glucoseRecords: glucoseProvided
      },
      ignoreBackdropClick: true
    });

    this.modalRef.onClose.subscribe((message: any) => {
      this.dataResponse[message.id].carbs = message.carbs;
      this.dataResponse[message.id].averageGlucose = message.avgGlucose;
      this.dataResponse[message.id].glucoseRecordsNumber = message.glucoseRecords;
      this.trackRow(message.id);
    });
  }

  trackRow (index: number) {
    if (this.dataResponse != null)
      return this.dataResponse[index]
    return null;
  }

  readPaginatingData() {
    this.service.get("http://localhost:9000/patient/" + this.patientId + "/data/query?start=" + (this.currentPage * this.pageStep) + "&step=" + this.pageStep).subscribe({
      next: res => {
        this.dataResponse = res;
        this.retrievedTable = true;
        console.log(this.retrievedTable);
      }
    })
  }

  readPagesCount() {
    this.service.get("http://localhost:9000/patient/" + this.patientId + "/data/paginator?step=" + this.pageStep).subscribe({
      next: res => {
        this.pagesRespone = res;
        this.getPageVisible();
        this.lastPageIndex = Math.floor(this.pagesRespone / this.pageStep);
        this.retrievedPages = true;
      }
    })
  }

  getPageVisible(): void {
    if (this.pageStep / this.pagesRespone <= 1)
      this.pagesVisible = this.pageStep;
    else
      this.pagesVisible = this.pagesRespone % this.pageStep;
  }

  setPageValue(value:any): void {
    this.currentPage = value - 1;
    this.readPaginatingData();
  }

  manipulatePageValue(value:any): void {
    this.currentPage += value;
    this.readPaginatingData();
  }

  userId: Number = 0;
}
