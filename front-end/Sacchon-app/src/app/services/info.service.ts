import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class InfoService {

  constructor(private http:HttpClient) { }

  get(url: string){
    return this.http.get(url)
  }

  getId(){
    const url = 'http://localhost:9000/patient/2';
    const lastTwo = url.substring(url.length - 1);

    return lastTwo;
  }
}
