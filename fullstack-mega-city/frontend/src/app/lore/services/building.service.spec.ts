import { environment } from './../../../environments/environment';
import { TestBed } from '@angular/core/testing';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';

import { BuildingService } from './building.service';
import { BuildingResponse } from '../model/building-response';
import { BUILDING_MOCKS } from './mocks';

describe('BuildingService', () => {

	let service: BuildingService;
	let httpMock: HttpTestingController

	beforeEach(() => {
		TestBed.configureTestingModule({
			imports: [ HttpClientTestingModule ],
			providers: [ BuildingService ] 
		});
		
		service = TestBed.inject(BuildingService);
		httpMock = TestBed.inject(HttpTestingController);
	});

	it('should get all building via GET', () => {
		const expectedBuildings: BuildingResponse[] = BUILDING_MOCKS;

		service.getAll().subscribe(buildings => {
			expect(buildings.length).toBe(3);
			expect(buildings).toEqual(expectedBuildings);
		});

		const request = httpMock.expectOne(`${environment.baseUrl}`);

		expect(request.request.method).toBe('GET');

		request.flush(expectedBuildings);
	});

	it('should get building by ID building via GET', () => {
		const expectedBuilding: BuildingResponse = BUILDING_MOCKS[0];

		service.get('1').subscribe(building => {
			expect(building).toEqual(BUILDING_MOCKS[0]);
		});

		const request = httpMock.expectOne(`${environment.baseUrl}/1`);

		expect(request.request.method).toBe('GET');

		request.flush(expectedBuilding);
	});
});
