import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {

  constructor(private http: HttpClient) { }

  post(data:any, url:string) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('crossDomain', 'true')
      .set('Access-Control-Allow-Credentials', 'true')
      .set('Access-Control-Allow-Origin', '*')
      .set('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');

      console.log(url);

    return this.http.post(url, JSON.stringify(data), {headers: headers});
  }

  postData(data:any, url:string) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('crossDomain', 'true')
      .set('Access-Control-Allow-Credentials', 'true')
      .set('Access-Control-Allow-Origin', '*')
      .set('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');

      console.log(url);

    return this.http.post(url, JSON.stringify(data), {headers: headers});
  }

  put(url:string) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('crossDomain', 'true')
      .set('Access-Control-Allow-Credentials', 'true')
      .set('Access-Control-Allow-Origin', '*')
      .set('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');

      console.log(url);

    return this.http.put(url, {headers: headers});
  }

  putData(data:any, url:string) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('crossDomain', 'true')
      .set('Access-Control-Allow-Credentials', 'true')
      .set('Access-Control-Allow-Origin', '*')
      .set('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');

      console.log(url);

    return this.http.put(url, data, {headers: headers});
  }

  get(url:string) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('crossDomain', 'true')
      .set('Access-Control-Allow-Credentials', 'true')
      .set('Access-Control-Allow-Origin', '*')
      .set('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');

    console.log(url);

    return this.http.get(url, {headers: headers});
  }

  delete(url:string) {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('crossDomain', 'true')
      .set('Access-Control-Allow-Credentials', 'true')
      .set('Access-Control-Allow-Origin', '*')
      .set('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');

    console.log(url);

    return this.http.delete(url, {headers: headers});
  }
}
