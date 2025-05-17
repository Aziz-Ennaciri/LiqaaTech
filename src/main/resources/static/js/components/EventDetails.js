import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
    Card,
    Descriptions,
    Button,
    Space,
    Tag,
    Typography,
    Divider,
    Modal,
    message,
    Spin
} from 'antd';
import {
    CalendarOutlined,
    EnvironmentOutlined,
    UserOutlined,
    DollarOutlined,
    ArrowLeftOutlined
} from '@ant-design/icons';
import { format } from 'date-fns';
import { fetchEventById, registerForEvent } from '../services/api';

const { Title, Text, Paragraph } = Typography;

const EventDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [event, setEvent] = useState(null);
    const [loading, setLoading] = useState(true);
    const [registerModalVisible, setRegisterModalVisible] = useState(false);

    useEffect(() => {
        loadEventDetails();
    }, [id]);

    const loadEventDetails = async () => {
        try {
            const response = await fetchEventById(id);
            setEvent(response.data);
        } catch (error) {
            console.error('Error loading event details:', error);
            message.error('Failed to load event details');
        } finally {
            setLoading(false);
        }
    };

    const handleRegister = async () => {
        try {
            await registerForEvent(id);
            message.success('Successfully registered for the event!');
            setRegisterModalVisible(false);
            loadEventDetails(); // Refresh event details
        } catch (error) {
            console.error('Error registering for event:', error);
            message.error('Failed to register for the event');
        }
    };

    const getStatusColor = (status) => {
        switch (status.toLowerCase()) {
            case 'upcoming':
                return 'blue';
            case 'ongoing':
                return 'green';
            case 'past':
                return 'gray';
            case 'cancelled':
                return 'red';
            default:
                return 'default';
        }
    };

    if (loading) {
        return (
            <div style={{ textAlign: 'center', padding: '50px' }}>
                <Spin size="large" />
            </div>
        );
    }

    if (!event) {
        return (
            <div style={{ textAlign: 'center', padding: '50px' }}>
                <Title level={3}>Event not found</Title>
                <Button type="primary" onClick={() => navigate('/events')}>
                    Back to Events
                </Button>
            </div>
        );
    }

    return (
        <div className="event-details-container">
            <Button
                icon={<ArrowLeftOutlined />}
                onClick={() => navigate('/events')}
                style={{ marginBottom: 16 }}
            >
                Back to Events
            </Button>

            <Card>
                <Space direction="vertical" size="large" style={{ width: '100%' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                        <div>
                            <Title level={2}>{event.title}</Title>
                            <Space>
                                <Tag color={getStatusColor(event.status)}>{event.status}</Tag>
                                <Tag color="purple">{event.categoryName}</Tag>
                            </Space>
                        </div>
                        {event.status === 'UPCOMING' && !event.isCancelled && (
                            <Button
                                type="primary"
                                size="large"
                                onClick={() => setRegisterModalVisible(true)}
                                disabled={event.availableSpots === 0}
                            >
                                Register Now
                            </Button>
                        )}
                    </div>

                    {event.imageUrl && (
                        <img
                            src={event.imageUrl}
                            alt={event.title}
                            style={{ width: '100%', maxHeight: 400, objectFit: 'cover' }}
                        />
                    )}

                    <Descriptions bordered>
                        <Descriptions.Item label="Date & Time" span={3}>
                            <Space>
                                <CalendarOutlined />
                                <Text>
                                    {format(new Date(event.startDateTime), 'MMMM dd, yyyy HH:mm')} -{' '}
                                    {format(new Date(event.endDateTime), 'HH:mm')}
                                </Text>
                            </Space>
                        </Descriptions.Item>
                        <Descriptions.Item label="Location" span={3}>
                            <Space>
                                <EnvironmentOutlined />
                                <Text>{event.location}</Text>
                            </Space>
                        </Descriptions.Item>
                        <Descriptions.Item label="Venue Details" span={3}>
                            {event.venueDetails}
                        </Descriptions.Item>
                        <Descriptions.Item label="Available Spots">
                            <Space>
                                <UserOutlined />
                                <Text>{event.availableSpots} / {event.capacity}</Text>
                            </Space>
                        </Descriptions.Item>
                        <Descriptions.Item label="Ticket Price">
                            <Space>
                                <DollarOutlined />
                                <Text strong>${event.ticketPrice}</Text>
                            </Space>
                        </Descriptions.Item>
                        <Descriptions.Item label="Category">
                            {event.categoryName}
                        </Descriptions.Item>
                    </Descriptions>

                    <Divider>Event Description</Divider>
                    <Paragraph>{event.description}</Paragraph>

                    {event.isCancelled && (
                        <Card type="inner" style={{ background: '#fff2f0' }}>
                            <Title level={4} style={{ color: '#cf1322' }}>Event Cancelled</Title>
                            <Paragraph>{event.cancellationReason}</Paragraph>
                        </Card>
                    )}
                </Space>
            </Card>

            <Modal
                title="Confirm Registration"
                open={registerModalVisible}
                onOk={handleRegister}
                onCancel={() => setRegisterModalVisible(false)}
                okText="Register"
                cancelText="Cancel"
            >
                <p>Are you sure you want to register for this event?</p>
                <p>Ticket Price: ${event.ticketPrice}</p>
            </Modal>
        </div>
    );
};

export default EventDetails; 