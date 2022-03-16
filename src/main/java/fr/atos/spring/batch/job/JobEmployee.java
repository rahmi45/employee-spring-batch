package fr.atos.spring.batch.job;

import fr.atos.spring.batch.dto.EmployeeDto;
import fr.atos.spring.batch.model.Employee;
import fr.atos.spring.batch.processor.EmployeeProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class JobEmployee {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EmployeeProcessor employeeProcessor;
    private DataSource dataSource;

    @Autowired
    public JobEmployee(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessor employeeProcessor, DataSource dataSource){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessor = employeeProcessor;
        this.dataSource = dataSource;
    }

    @Qualifier(value = "jobA")
    @Bean
    public Job JobA() throws Exception {
        return this.jobBuilderFactory.get("jobA")
                .start(stepAJobA())
                .build();
    }

    @Bean
    public Step stepAJobA() throws Exception {
        return this.stepBuilderFactory.get("stepA")
                .<EmployeeDto, Employee>chunk(5)
                .reader(employeeReader())
                .processor(employeeProcessor)
                .writer(employeeDBWriterDefault())
                .build();
    }

/*    @Bean
    @StepScope
    Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) throws Exception {
        System.out.println(fileName);
        return new ClassPathResource(fileName);

    }*/

    @Bean
    @StepScope
    public FlatFileItemReader<EmployeeDto> employeeReader() throws Exception {
        FlatFileItemReader<EmployeeDto> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("employees.csv"));
        reader.setLineMapper(lineMapper());
        return reader;
    }
    @Bean
    public LineMapper<EmployeeDto> lineMapper() {
        DefaultLineMapper<EmployeeDto> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstName", "lastName", "email", "age");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<EmployeeDto> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(EmployeeDto.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public JdbcBatchItemWriter<Employee> employeeDBWriterDefault() {
        JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into employee (id, first_name, last_name, email, age) values (:id, :firstName, :lastName, :email, :age)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
        return itemWriter;
    }
}
