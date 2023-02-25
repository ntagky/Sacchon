import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private http: HttpClient) { }

  getDoctor(id :any){
    // id = patient id
    const url = 'http://localhost:9000/patient/' + id + '/doctor';
    return this.http.get(url);
  }
}
