package net.passioncloud.hello;

import net.passioncloud.hello.model.SampleMember;
import net.passioncloud.hello.processor.SampleMemberProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<SampleMember> reader() {
        System.out.println("Reader called");
        return new FlatFileItemReaderBuilder<SampleMember>()
                .name("sampleMemberReader")
                .resource(new ClassPathResource("member-data.csv"))
                .delimited()
                .names(new String[] { "firstName", "lastName", "emailAddress"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<SampleMember>()
                {{
                    setTargetType(SampleMember.class);
                }})
                .build();
    }
    @Bean
    public SampleMemberProcessor processor() {
        System.out.println("processor called");
        return new SampleMemberProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<SampleMember> writer(DataSource dataSource) {
        System.out.println("writer called");
        return new JdbcBatchItemWriterBuilder<SampleMember>()
                .itemSqlParameterSourceProvider(
                        new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO sample_member(first_name, last_name, email_address) VALUES (:firstName, :lastName, :lastName)")
                .dataSource(dataSource)
                .build();

    }

    // actual job configuration
    // Jobs are built from steps. Each step can involve a reader, a processor and a writer.
    // 2 methods
    @Bean
    public Job importSampleMemberJob(JobCompletionNotificationListener listener, Step step1) {
        System.out.println("importSampleMemberJob called");
        return jobBuilderFactory.get("importSampleMemberJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<SampleMember> writer) {
        System.out.println("step1 called");
        return stepBuilderFactory.get("step1")
                .<SampleMember, SampleMember> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

}
