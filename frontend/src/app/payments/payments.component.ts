import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { StudentsService } from '../services/students.service';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.css'
})
export class PaymentsComponent implements OnInit{

  public payments : any;  
  public displayedColumns = ['id','date','paymentType','paymentStatus','amount','firstName'];
  public dataSource : any;
  
  
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(private studentsService: StudentsService) {

  }

  ngOnInit(): void {

    this.studentsService.getAllPayments()
     .subscribe({
       next : value => {
         this.payments = value;
         this.dataSource = new MatTableDataSource(this.payments);
         this.dataSource.paginator = this.paginator;
         this.dataSource.sort = this.sort;
       },
       error : err => {
         console.log(err);
       }
     })
  }

}
