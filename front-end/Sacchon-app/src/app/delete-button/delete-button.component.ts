import { DeleteService } from './../services/delete.service';
import { Component,OnInit } from '@angular/core';
import { InfoService } from '../services/info.service';

@Component({
  selector: 'app-delete-button',
  templateUrl: './delete-button.component.html',
  styleUrls: ['./delete-button.component.scss']
})
export class DeleteButtonComponent implements OnInit {

  data:any;
  response:any;

  constructor(private deleteService: DeleteService ){}

  ngOnInit(): void {
    this.deleteData();

  }

  deleteData(){



    this.deleteService.delete().subscribe(
   {
      next: data =>this.response = data,

   }


 )}

}
