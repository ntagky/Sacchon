
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})


export class DeleteService {

  constructor(private http:HttpClient) { }

<<<<<<< HEAD
  delete(){
    return this.http.delete('http://localhost:9000/delete/2')
=======
  delete(url: string){
    return this.http.delete(url);
>>>>>>> 0c9de449c618f3bc3f1ce76aecbee9e9bd31641e
  }
}
