import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Payment, Student } from '../model/students.model';

@Injectable({
  providedIn: 'root'
})
export class StudentsService {

  constructor(private http: HttpClient) { }

  public getAllPayments() :Observable<Array<Payment>>{
    return this.http.get<Array<Payment>>(`${environment.backenhost}/payments`);
  }

  public getStudents() :Observable<Array<Student>>{
    return this.http.get<Array<Student>>(`${environment.backenhost}/students`);
  }

  public getStudentPayemnts(code: string) :Observable<Array<Payment>>{
    return this.http.get<Array<Payment>>(`${environment.backenhost}/students/${code}/payments`);
  }

  public savePayment(formData: any) :Observable<Payment>{
    return this.http.post<Payment>(`${environment.backenhost}/payments`, formData);
  }




}
