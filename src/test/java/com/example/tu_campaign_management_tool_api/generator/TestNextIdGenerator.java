package com.example.tu_campaign_management_tool_api.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestNextIdGenerator {
    private TestNextIdGeneratorMock nextIdGeneratorMock;
    private String expectedId;
    private String actualId;

    @BeforeEach
    public void initEach() {
        this.nextIdGeneratorMock = new TestNextIdGeneratorMock();
    }

    private void arrangeExpectedId(String paramExpectedId) {
        expectedId = paramExpectedId;
    }

    private void actActualId() {
        actualId = this.nextIdGeneratorMock.generate();
    }

    @Test
    public void should_generate_an_id_starting_with_one_having_five_leading_zeros() {
        // Arrange
        arrangeExpectedId("000001");
        // Act and assert
        actActualId();
        assertThat(actualId, is(this.expectedId));
    }

    @Test
    public void should_generate_an_id_having_new_leading_zeros_after_previous_id_having_all_leading_nines() {
        // Arrange
        arrangeExpectedId("0000001");
        // Act
        this.nextIdGeneratorMock.setCurrentId(new AtomicLong(999999));
        actActualId();
        // Assert
        assertThat(actualId, is(this.expectedId));
    }

    @Test
    public void should_generate_two_ids_having_new_leading_zeros_after_reaching_the_maximum() {
        // Arrange
        List<String> expectedIds = new ArrayList<>(Arrays.asList("0000001", "00000001"));
        String expectedIdsToString = expectedIds.toString();
        List<String> actualIds = new ArrayList<>();
        // Act
        this.nextIdGeneratorMock.setCurrentId(new AtomicLong(999999));
        actualIds.add(this.nextIdGeneratorMock.generate());
        this.nextIdGeneratorMock.setCurrentId(new AtomicLong(9999999));
        actualIds.add(this.nextIdGeneratorMock.generate());
        String actualIdsToString = actualIds.toString();
        // Assert
        assertThat(actualIdsToString, is(expectedIdsToString));
    }
}
