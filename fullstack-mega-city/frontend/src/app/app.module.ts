import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BuildingFormComponent } from './lore/containers/building-form/building-form.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { BuildingListComponent } from './lore/containers/building-list/building-list.component';
import { MatTableModule } from '@angular/material/table';
import { ValidateStartsWithDirective } from './lore/directives/validators/validate-starts-with.directive';
import { BuildingCreateFormComponent } from './lore/containers/building-create-form/building-create-form.component';
import {MatIconModule} from '@angular/material/icon';

@NgModule({
	declarations: [AppComponent, BuildingFormComponent, BuildingListComponent, ValidateStartsWithDirective, BuildingCreateFormComponent],
	imports: [
		BrowserModule,
		AppRoutingModule,
		HttpClientModule,
		TranslateModule,
		TranslateModule.forRoot({
			loader: {
				provide: TranslateLoader,
				useFactory: HttpLoaderFactory,
				deps: [HttpClient],
			},
		}),
		BrowserAnimationsModule,
		MatToolbarModule,
		MatFormFieldModule,
		MatInputModule,
		ReactiveFormsModule,
		MatDividerModule,
		MatButtonModule,
		MatTableModule,
		MatIconModule
	],
	providers: [],
	bootstrap: [AppComponent],
})
export class AppModule {}

export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
	return new TranslateHttpLoader(http);
}
