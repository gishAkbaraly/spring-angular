import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GomeComponent } from './gome.component';

describe('GomeComponent', () => {
  let component: GomeComponent;
  let fixture: ComponentFixture<GomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
