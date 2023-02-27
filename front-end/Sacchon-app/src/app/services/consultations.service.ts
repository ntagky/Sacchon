import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConsultationsService {

  constructor(private http: HttpClient) { }

  getConsultations(id :any){
    // id = patient id
    const url = 'http://localhost:9000/patient/' + id + '/consultations';
    return this.http.get(url);
  }

  getLastConsultationSeenStatus(id :any){
    // id = patient id
    const url = 'http://localhost:9000/consultation/' + id + '/status';
    return this.http.get(url);
  }
}
