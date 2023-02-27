import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  constructor(private http: HttpClient) { }

  signUp(data: any, url: string){
    const headers = new HttpHeaders()
    .set('Content-Type', 'application/json')
    .set('crossDomain', 'true')
    .set('Access-Control-Allow-Credentials', 'true')
    .set('Access-Control-Allow-Origin', '*')
    .set('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');

    console.log(url);

  return this.http.post(url, JSON.stringify(data), {headers: headers});
  }
}
