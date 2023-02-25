import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MedicationService {
  constructor(private http: HttpClient) { }

  getMedications(id :any){
    // id = consultation id
    const url = 'http://localhost:9000/consultation/' + id + '/medication';
    return this.http.get(url);
  }
}
