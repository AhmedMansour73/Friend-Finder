import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url = 'http://localhost:8080/auth';
  constructor(private http: HttpClient) { }


  login(userData: any): Observable<any>{
      // username,password they are  a key for file json (send to body)
      return this.http.post<any>(this.url + "/login" ,userData).pipe(
        map(
          response => response
        )
      );
    }


  signup(userData: any): Observable<any>{
    return this.http.post<any>(this.url + "/signup", userData).pipe(
      map(
        response => response
      )
    );
  }

  isUserLogin(): boolean {
    return sessionStorage.getItem("token") !== null &&
      sessionStorage.getItem("token") !== undefined &&
      sessionStorage.getItem("token") !== "";
  }

  logout(){
    sessionStorage.removeItem("token");
  }

  isUserAdmin(): boolean{
    return sessionStorage.getItem("role") &&
      sessionStorage.getItem("role").includes("ADMIN");
  }
}
