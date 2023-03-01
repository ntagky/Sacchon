
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DeleteService {

  constructor(private http:HttpClient) { }

  delete(id: any){
    return this.http.delete('http://localhost:9000/delete/' + id)
  }
}
