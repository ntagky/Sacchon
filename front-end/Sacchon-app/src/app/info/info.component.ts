import { DeleteService } from './../services/delete.service';
import { LocalStorageService } from './../services/local-storage.service';
import { InfoService } from './../services/info.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


@Component({ selector: 'app-info',
            templateUrl: './info.component.html',
            styleUrls: ['./info.component.scss'] })
export class InfoComponent implements OnInit {
  data: any;
  userId: number;
  infoRespone: any;
  avatarUrl: string = "https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp";

  constructor(private infoService: InfoService, private deleteService: DeleteService, private router: Router, private localStoreService: LocalStorageService) {
    this.userId = Number(localStoreService.getData("user"));
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.infoService.get('http://localhost:9000/patient/' + this.userId).subscribe({
      next: response => {
        this.data = response;
        // if (this.data[0].gender == "Male")
        //   this.avatarUrl = "https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp"
        // else if (this.data[0].gender == "Female")
        //   this.avatarUrl = "https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava2.webp"

      }
    })
  }

  deleteData() {
    this.deleteService.delete(this.userId).subscribe({
      next: data => {
        this.infoRespone = data;
        this.localStoreService.clearData();
        this.router.navigateByUrl('/delete');
      }
    })
  }
}
