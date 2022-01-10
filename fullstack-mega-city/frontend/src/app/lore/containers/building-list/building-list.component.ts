import { RefreshService } from './../../services/refresh.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BuildingResponse } from '../../model/building-response';
import { BuildingService } from '../../services/building.service';
import { Subscription } from 'rxjs';

@Component({
	selector: 'app-building-list',
	templateUrl: './building-list.component.html',
	styleUrls: ['./building-list.component.scss'],
})
export class BuildingListComponent implements OnInit, OnDestroy {
	displayedColumns: string[] = ['id', 'name', 'address', 'sectorCode', 'actions'];
	dataSource: BuildingResponse[] = [];

	private subs = new Subscription();

	constructor(
		private router: Router,
		private buildingService: BuildingService,
		private refreshService: RefreshService
		) {}

	ngOnInit() {
		this.refreshService.getRefresh()
			.subscribe(() => {
				this.displayBuildigins();
			})

		this.displayBuildigins();
	}

	ngOnDestroy(): void {
		this.subs.unsubscribe();
	}

	displayBuildigins() {
		this.subs.add(this.buildingService.getAll().subscribe(buildings => this.dataSource = buildings));
	}

	deleteBuilding(id: number) {
		this.subs.add(this.buildingService.delete(id).subscribe());
	}
}
