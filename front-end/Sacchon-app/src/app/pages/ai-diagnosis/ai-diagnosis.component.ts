import { NetworkService } from './../../services/network.service';
import { Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ai-diagnosis',
  templateUrl: './ai-diagnosis.component.html',
  styleUrls: ['./ai-diagnosis.component.scss']
})
export class AiDiagnosisComponent implements OnInit {

  diagnosisForm: any;

  constructor(
    private fb: FormBuilder,
    private networkService: NetworkService
    ) {}

  ngOnInit(): void {
    this.diagnosisForm = this.fb.group({
      pregnancies: ["", [Validators.required]],
      glucose: ["", [Validators.required]],
      bloodPressure: ["", [Validators.required]],
      skinThickness: ["", [Validators.required]],
      insulin: ["", [Validators.required]],
      bmi: ["", [Validators.required]],
      age: ["", [Validators.required]],
      dpf: ["", [Validators.required]],
    });
  }

  requestOutput(): any {
    console.log("click");
    var pregnancies = this.diagnosisForm.get("pregnancies").value;
    var glucose = this.diagnosisForm.get("glucose").value;
    var bloodPressure = this.diagnosisForm.get("bloodPressure").value;
    var skinThickness = this.diagnosisForm.get("skinThickness").value;
    var insulin = this.diagnosisForm.get("insulin").value;
    var bmi = this.diagnosisForm.get("bmi").value;
    var age = this.diagnosisForm.get("age").value;
    var dpf = this.diagnosisForm.get("dpf").value;

    console.log('http://localhost:9000/network/inputs?pregnancies=' + pregnancies + '&glucose=' + glucose + '&bloodPressure=' + bloodPressure + '&skinThickness=' + skinThickness + '&insulin=' + insulin + '&bmi=' + bmi + '&age=' + age + '&dpf=' + dpf);
    this.networkService.get('http://localhost:9000/network/inputs?pregnancies=' + pregnancies + '&glucose=' + glucose + '&bloodPressure=' + bloodPressure + '&skinThickness=' + skinThickness + '&insulin=' + insulin + '&bmi=' + bmi + '&age=' + age + '&dpf=' + dpf).subscribe({
      next: res => {
        console.log(res);
      }
    });
  }
}
