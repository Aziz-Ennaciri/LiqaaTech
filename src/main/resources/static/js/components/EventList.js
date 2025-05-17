import React, { useState, useEffect } from 'react';
import { Card, Row, Col, Form, Input, Button, Pagination, Select, Spin, Empty } from 'antd';
import { SearchOutlined, CalendarOutlined, EnvironmentOutlined } from '@ant-design/icons';
import { format } from 'date-fns';
import EventCard from './EventCard';
import { fetchEvents, fetchCategories } from '../services/api';

const { Option } = Select;

const EventList = () => {
    const [events, setEvents] = useState([]);
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(false);
    const [total, setTotal] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize, setPageSize] = useState(9);
    const [filters, setFilters] = useState({
        searchTerm: '',
        categoryId: null,
        status: 'all'
    });

    useEffect(() => {
        loadCategories();
        loadEvents();
    }, [currentPage, pageSize, filters]);

    const loadCategories = async () => {
        try {
            const response = await fetchCategories();
            setCategories(response.data);
        } catch (error) {
            console.error('Error loading categories:', error);
        }
    };

    const loadEvents = async () => {
        setLoading(true);
        try {
            const params = {
                page: currentPage - 1,
                size: pageSize,
                ...filters
            };
            const response = await fetchEvents(params);
            setEvents(response.data.content);
            setTotal(response.data.totalElements);
        } catch (error) {
            console.error('Error loading events:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = (value) => {
        setFilters(prev => ({ ...prev, searchTerm: value }));
        setCurrentPage(1);
    };

    const handleCategoryChange = (value) => {
        setFilters(prev => ({ ...prev, categoryId: value }));
        setCurrentPage(1);
    };

    const handleStatusChange = (value) => {
        setFilters(prev => ({ ...prev, status: value }));
        setCurrentPage(1);
    };

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    return (
        <div className="event-list-container">
            <div className="filters-section">
                <Row gutter={[16, 16]} align="middle">
                    <Col xs={24} sm={8}>
                        <Input
                            placeholder="Search events..."
                            prefix={<SearchOutlined />}
                            onChange={(e) => handleSearch(e.target.value)}
                            allowClear
                        />
                    </Col>
                    <Col xs={24} sm={8}>
                        <Select
                            placeholder="Select Category"
                            style={{ width: '100%' }}
                            onChange={handleCategoryChange}
                            allowClear
                        >
                            {categories.map(category => (
                                <Option key={category.id} value={category.id}>
                                    {category.name}
                                </Option>
                            ))}
                        </Select>
                    </Col>
                    <Col xs={24} sm={8}>
                        <Select
                            placeholder="Event Status"
                            style={{ width: '100%' }}
                            onChange={handleStatusChange}
                            defaultValue="all"
                        >
                            <Option value="all">All Events</Option>
                            <Option value="upcoming">Upcoming</Option>
                            <Option value="ongoing">Ongoing</Option>
                            <Option value="past">Past</Option>
                        </Select>
                    </Col>
                </Row>
            </div>

            {loading ? (
                <div className="loading-container">
                    <Spin size="large" />
                </div>
            ) : events.length > 0 ? (
                <>
                    <Row gutter={[16, 16]} className="event-grid">
                        {events.map(event => (
                            <Col xs={24} sm={12} md={8} key={event.id}>
                                <EventCard event={event} />
                            </Col>
                        ))}
                    </Row>
                    <div className="pagination-container">
                        <Pagination
                            current={currentPage}
                            pageSize={pageSize}
                            total={total}
                            onChange={handlePageChange}
                            showSizeChanger
                            onShowSizeChange={(current, size) => {
                                setPageSize(size);
                                setCurrentPage(1);
                            }}
                        />
                    </div>
                </>
            ) : (
                <Empty description="No events found" />
            )}
        </div>
    );
};

export default EventList; 