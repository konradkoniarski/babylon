package co.uk.babylon.di.module;

import javax.inject.Singleton;

import co.uk.babylon.data.network.NetworkService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Singleton
@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    @Singleton
    @Provides
    static Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static NetworkService provideRetrofitService(final Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }
}
