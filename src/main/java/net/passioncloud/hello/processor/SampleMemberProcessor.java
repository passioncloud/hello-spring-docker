package net.passioncloud.hello.processor;

import net.passioncloud.hello.model.SampleMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// You receive an incoming object which you can transform
public class SampleMemberProcessor implements ItemProcessor<SampleMember, SampleMember> {
    private static final Logger log = LoggerFactory.getLogger(SampleMemberProcessor.class);

    @Override
    public SampleMember process(final SampleMember sampleMember) throws Exception {
        // Make names capitalized
        final String firstName = sampleMember.getFirstName().toUpperCase();
        final String lastName = sampleMember.getLastName().toUpperCase();
        final SampleMember transformedSampleMember = new SampleMember(firstName, lastName, sampleMember.getEmailAddress());
        return transformedSampleMember;
    }


}
