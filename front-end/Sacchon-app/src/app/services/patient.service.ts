import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) { }

  getPatient(id :any){
    // id = patient id
    const url = 'http://localhost:9000/patient/' + id;
    return this.http.get(url);
  }
}
