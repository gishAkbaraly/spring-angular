import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import {catchError, finalize, Observable, throwError} from 'rxjs';
import {Route, Router} from "@angular/router";
import { AuthService } from '../services/auth.service';


/*
export const appHttpInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};*/

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {

  constructor(private authService : AuthService, private router : Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    
    if(request.url.includes('/login')) {
      return next.handle(request);
    } else{
      let newRequest = request.clone({
        headers : request.headers.set('Authorization','Bearer '+this.authService.accessToken)
      });
  
      return next.handle(newRequest).pipe(
        catchError((err) => {
          if(err.status==403){
            this.router.navigateByUrl("/admin/notAuthorized");
          } else if(err.status==401){
            this.router.navigateByUrl("/login")
          }
          return throwError(() => err);
        }),
        finalize(()=>{
  
        })
      );
    }
  }

  private handleError(err:HttpErrorResponse){

  }
}