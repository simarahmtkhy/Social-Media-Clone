import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReqComponent } from './req.component';

describe('ReqComponent', () => {
  let component: ReqComponent;
  let fixture: ComponentFixture<ReqComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReqComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
