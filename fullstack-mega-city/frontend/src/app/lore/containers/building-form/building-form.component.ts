import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BuildingService } from '../../services/building.service';
import { ErrorStateMatcher } from '@angular/material/core';
import { startsWithValidator } from '../../directives/validators/validate-starts-with.directive';
import { BuildingRequest } from '../../model/building-request';
import { BuildingResponse } from '../../model/building-response';
import { Subscription } from 'rxjs';
import { TouchSequence } from 'selenium-webdriver';
import { CustomErrorStateMatcher } from '../../directives/validators/CustomErrorStateMatcher';

@Component({
	selector: 'app-building-form',
	templateUrl: './building-form.component.html',
	styleUrls: ['./building-form.component.scss'],
})
export class BuildingFormComponent implements OnInit, OnDestroy {
	building: BuildingResponse = null;
	form: FormGroup = this.initForm();

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
		let id = this.route.snapshot.paramMap.get('id');

		if (id !== null) {
			this.subs.add(
				this.buildingService.get(id).subscribe((data: BuildingResponse) => {
				if (data.index != '' && data.energyUnitMax != null) {
					this.form.controls.energyUnitMax.disable();
					this.form.get('index').disable();
				}

				this.form = this.initForm(data);
				this.building = data;
				})
			)
		}
	}

	ngOnDestroy(): void {
		this.subs.unsubscribe();
	}

	initForm(building?: BuildingResponse) {
		return this.formBuilder.group({
			name: new FormControl(
				building?.name || '',
				[Validators.required, Validators.maxLength(50)]
			),
			address: new FormControl(
				building?.address || '',
				[Validators.required, Validators.maxLength(50)]
			),
			index: new FormControl(
				building?.index || '',
				[Validators.required, startsWithValidator('NO')]
			),
			sectorCode: new FormControl(
				{ 
					value: building?.index || '',
					disabled: building?.index
				},
				[Validators.required, Validators.maxLength(20)]
			),
			energyUnitMax: new FormControl(
				{
					value: building?.energyUnitMax || '',
					disabled: building?.energyUnitMax,
				}
			),
			energyUnits: new FormControl(
				building?.energyUnits || '',
			),
		}, { validators: this.checkIfMaxEnergyNotExceeded});
	}

	hasError(path: string, errorCode: string) {
		return this.form && this.form.hasError(errorCode, path);
	}

	navigateToBuildingsList() {
		this.router.navigate(['buildings']).then();
	}

	checkIfMaxEnergyNotExceeded(group: FormGroup) {
		let energyUnits = group.controls.energyUnits.value;
		let energyUnitsMax = group.controls.energyUnitMax.value;

		return energyUnits < energyUnitsMax ? null : { exceededMax: true };
	}


	submit() {
		this.buildingRequest = {
			id: Number(this.route.snapshot.paramMap.get('id')),
			name: this.form.controls.name.value,
			address: this.form.controls.address.value,
			index: this.form.controls.index.value,
			sectorCode: this.form.controls.sectorCode.value,
			energyUnits: this.form.controls.energyUnits.value,
			energyUnitMax: this.form.controls.energyUnitMax.value
		}

		this.subs.add(
			this.buildingService.put(this.buildingRequest)
			.subscribe(
				(data: BuildingResponse) => {
					this.navigateToBuildingsList();
				}
			)
		)
	}
}


