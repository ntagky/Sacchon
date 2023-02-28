<<<<<<< HEAD
=======
import { LocalStorageService } from './../services/local-storage.service';
>>>>>>> 0c9de449c618f3bc3f1ce76aecbee9e9bd31641e
import { DeleteService } from './../services/delete.service';
import { InfoService } from './../services/info.service';
import { Component, OnInit } from '@angular/core';
<<<<<<< HEAD
import { Router, RouterModule } from '@angular/router';

@Component({ selector: 'app-info', templateUrl: './info.component.html', styleUrls: ['./info.component.scss'] })
=======
import { Router } from  '@angular/router';
>>>>>>> 0c9de449c618f3bc3f1ce76aecbee9e9bd31641e

export class InfoComponent implements OnInit {
  data: any; patientId = 4;
  deleteRespone: any;

<<<<<<< HEAD
  constructor(private infoService: InfoService, private deleteService: DeleteService, private router: Router) { }

  ngOnInit(): void {
    this.getData();
  }

  getData() { this.infoService.get().subscribe({ next: response => this.data = response }) }
  deleteData() { this.deleteService.delete().subscribe({ next: data => { this.deleteRespone = data; this.router.navigateByUrl('/delete') } }) }
=======
  userId: number;
  data:any;
  deleteRespone: any;

  constructor(
    private infoService: InfoService,
    private deleteService: DeleteService,
    private router: Router,
    private localStoreService: LocalStorageService
  ){
    this.userId = Number(localStoreService.getData("user"));
  }

  ngOnInit():void{
    this.getData();
  }

  getData(){
    this.infoService.get('http://localhost:9000/patient/' + this.userId).subscribe({
       next: response => this.data = response
    }
  )}

  deleteData(){
    this.deleteService.delete('http://localhost:9000/delete/' + this.userId).subscribe({
      next: data => {
        this.deleteRespone = data;
        this.localStoreService.clearData();
        this.router.navigateByUrl('/goodbye');
      }
    }
  )}

>>>>>>> 0c9de449c618f3bc3f1ce76aecbee9e9bd31641e
}
