import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NetworkService {

  constructor(private http:HttpClient) { }

  get(url: any){
    return this.http.get(url);
  }
}
