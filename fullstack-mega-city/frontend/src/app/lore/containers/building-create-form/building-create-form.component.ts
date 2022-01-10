import { HttpErrorResponse } from '@angular/common/http';
import { group } from '@angular/animations';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, AbstractControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { max } from 'rxjs/operators';
import { startsWithValidator } from '../../directives/validators/validate-starts-with.directive';
import { BuildingRequest } from '../../model/building-request';
import { BuildingResponse } from '../../model/building-response';
import { BuildingService } from '../../services/building.service';
import { CustomErrorStateMatcher } from '../../directives/validators/CustomErrorStateMatcher';

@Component({
  selector: 'app-building-create-form',
  templateUrl: './building-create-form.component.html',
  styleUrls: ['./building-create-form.component.scss']
})
export class BuildingCreateFormComponent implements OnInit, OnDestroy {
	form: FormGroup = this.initForm();
	maxEnergy: number;
	apiErrorMessage = '';
	buildingRequest: BuildingRequest;

	matcher = new CustomErrorStateMatcher();

	private subs = new Subscription();

	constructor(
		private router: Router,
		private route: ActivatedRoute,
		private buildingService: BuildingService,
		private formBuilder: FormBuilder,
	) {}

	ngOnInit() {
		
	}

	ngOnDestroy(): void {
		this.subs.unsubscribe();
	}

	initForm() {
		return this.formBuilder.group({
			name: new FormControl(
				'',
				[Validators.required, Validators.maxLength(50)]
			),
			address: new FormControl(
				'',
				[Validators.required, Validators.maxLength(50)]
			),
			index: new FormControl(
        '',
				[Validators.required, Validators.minLength(3), startsWithValidator('NO')]
			),
			sectorCode: new FormControl(
        '',
        [Validators.required, Validators.maxLength(20)]
        ),
			energyUnitMax: new FormControl(
				'',
        [Validators.required, Validators.maxLength(20)]
			),
			energyUnits: new FormControl('',
        [Validators.required, Validators.maxLength(20)]
      ),
		}, { validators: this.validateMaxNotExceeded});
	}

	hasError(path: string, errorCode: string) {
		return this.form && this.form.hasError(errorCode, path);
	}

	navigateToBuildingsList() {
		this.router.navigate(['buildings']).then();
	}

	validateMaxNotExceeded(group: FormGroup) {
		let energyUnits: number = group.controls.energyUnits.value;
		let energyUnitsMax: number = group.controls.energyUnitMax.value;

		return energyUnits < energyUnitsMax ? null : { exceededMax: true };
	}

	submit() {
		this.buildingRequest = {
      id: null,
			name: this.form.controls.name.value,
			address: this.form.controls.address.value,
			index: this.form.controls.index.value,
			sectorCode: this.form.controls.sectorCode.value,
			energyUnits: this.form.controls.energyUnits.value,
			energyUnitMax: this.form.controls.energyUnitMax.value
		}

		this.subs.add(
			this.buildingService.post(this.buildingRequest)
			.subscribe(
				(data: BuildingResponse) => {
					this.navigateToBuildingsList();
				},
				(error: HttpErrorResponse) => {
					if (error.status == 409) {
						this.apiErrorMessage = 'Building name already exists'
					}
				}
			)
		)
	}

}
