import { ModalMeasurementsComponent } from '../measurements-modal/measurements-modal.component';
import { MeasurementService } from '../services/measurements.service';
import { Component, OnInit, Input } from '@angular/core';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';

@Component({
  selector: 'app-measurements-table',
  templateUrl: './measurements-table.component.html',
  styleUrls: ['./measurements-table.component.scss']
})
export class MeasurementsTableComponent implements OnInit {

  @Input() patientId: any;

  modalRef: MdbModalRef<ModalMeasurementsComponent> | null = null;
  dataResponse: any;
  pagesRespone: any;
  currentPage: number = 0;
  pageStep: number = 10;
  section: number = 0;
  pagesVisible: any;
  lastPageIndex: any;
  retrievedPages = false;
  retrievedTable = false;

  constructor(private modalService: MdbModalService, private service: MeasurementService) {}

  ngOnInit(): void {
    this.readPagesCount();
    this.readPaginatingData();
  }

  openModal(selectedId: number, dateSelected: string, carbsIntake: any, glucoseProvided:any) {
    this.modalRef = this.modalService.open(ModalMeasurementsComponent, {
      data: {
        patientId: this.patientId,
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
    if ((this.section + 1) * this.pageStep / this.pagesRespone <= 1)
      this.pagesVisible = this.pageStep;
    else
      this.pagesVisible = this.pagesRespone % this.pageStep;
  }

  setPageValue(value:any): void {
    this.currentPage = value - 1;
    this.readPaginatingData();
  }

  manipulatePageValue(value:any): void {
    this.section += value;
    if (value > 0)
      this.currentPage = (this.pageStep * this.section);
    else
      this.currentPage = (this.pageStep * this.section + this.pageStep - 1);
    this.getPageVisible();
    this.readPaginatingData();
    this.lastPageIndex = Math.floor(this.pagesRespone / this.pageStep);
  }

}
