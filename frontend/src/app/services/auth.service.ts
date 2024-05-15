import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  
  public users:any = {
    admin : {password: '1234', roles: ['STUDENT', 'ADMIN']},
    user1: {password: '1234', roles: ['STUDENT']}
  } 

  constructor(private router : Router, private http:HttpClient) { }

  public username: any;
  public isAuthenticated:boolean = false;
  public roles: string[] = [];
  accessToken!: string;

  public login(username: string, password: string) {
    let options = {
      headers : new HttpHeaders().set("Content-Type", "application/x-www-form-urlencoded")
    }
    let params = new HttpParams().set("username", username).set("password", password);
    return this.http.post(`${environment.backenhost}/login`, params, options);
 
  }

  loadProfile(data: any) {
    this.accessToken = data['access-token'];
    let decodeJwt:any = jwtDecode(this.accessToken);
    this.isAuthenticated = true;
    this.username = decodeJwt.sub;
    this.roles = decodeJwt.scope.split(' ');
    window.localStorage.setItem('jwt-token',this.accessToken);
  }

  logout() {
    this.isAuthenticated = false;
    this.username = undefined;
    this.roles = [];
    window.localStorage.removeItem('jwt-token');
    this.router.navigateByUrl("/login")
  }

  loadJwtTokenFromLocalStorage() {
    let token = window.localStorage.getItem('jwt-token');
    if(token){
      this.loadProfile({"access-token" : token});
      this.router.navigateByUrl("/admin")
    }
  }
}
