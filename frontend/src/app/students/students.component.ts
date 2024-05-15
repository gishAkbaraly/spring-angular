import { Component, OnInit, ViewChild } from '@angular/core';
import { StudentsService } from '../services/students.service';
import { Student } from '../model/students.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrl: './students.component.css'
})
export class StudentsComponent implements OnInit{
  
  constructor(private studentsService : StudentsService, private router: Router) {}
  
  students! : Array<Student>
  public displayedColumns = ['id','firstName','lastName','code','programId', 'payments'];
  public dataSource! : MatTableDataSource<Student>;

  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;



  ngOnInit(): void {
    this.studentsService.getStudents().subscribe({
      next: value => {
        this.students = value;
        this.dataSource = new MatTableDataSource<Student>(this.students);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;

      },
      error: err => {
        console.error(err);
      }
    });
  }

  studentPayments(student: Student) {
    this.router.navigateByUrl(`/admin/student-details/${student.code}`)
  }

}
