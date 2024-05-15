import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentsService } from '../services/students.service';
import { Payment, Student } from '../model/students.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-student-details',
  templateUrl: './student-details.component.html',
  styleUrl: './student-details.component.css'
})
export class StudentDetailsComponent implements OnInit{


  studentCode!: string;
  studentPayments! : Array<Payment>;
  public dataSource! : MatTableDataSource<Payment>;
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;
  public displayedColumns = ['id','date','paymentType','paymentStatus','amount','firstName'];

  constructor(  
      private activatedRoute: ActivatedRoute, 
      private studentsService: StudentsService,
      private router : Router
  ) {}

  ngOnInit(): void {
      this.studentCode = this.activatedRoute.snapshot.params['code'];
      this.studentsService.getStudentPayemnts(this.studentCode).subscribe({
        next: value => {
          this.studentPayments = value;
          this.dataSource = new MatTableDataSource<Payment>(this.studentPayments);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
          },
        error: err => {
          console.error(err);
        }
      });
  }

  newPayment() {
    this.router.navigateByUrl(`/admin/new-payment/${this.studentCode}`);
  }

}
